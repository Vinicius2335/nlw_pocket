import { X } from "lucide-react"
import { Button } from "./ui/button"
import { DialogClose, DialogContent, DialogDescription, DialogTitle } from "./ui/dialog"
import { Input } from "./ui/input"
import { Label } from "./ui/label"
import { RadioGroup, RadioGroupIndicator, RadioGroupItem } from "./ui/radio-group"
import { Controller, useForm } from "react-hook-form"
import { zodResolver } from "@hookform/resolvers/zod"
import { createGoalFormSchema, type CreateGoalForm } from "../form-schemas/create-goal-form-schema"
import { daysOfWeek } from "../data/days-of-week"
import { createGoal } from "../http/create-goal"
import { useQueryClient } from "@tanstack/react-query"

export function CreateGoal() {
  const queryClient = useQueryClient()

  // control > controla todas as funçoes do formulario estao aki
  // precisamos por causa do raioButon do radix
  // register > registrar os campos nativos do form
  // handleSubmit > para adicionar a função que ira submeter o formulario
  // formStage > para mostrar as mensagens de erro
  // reset > resetar o formulario
  const { register, control, handleSubmit, formState, reset } = useForm<CreateGoalForm>({
    resolver: zodResolver(createGoalFormSchema)
  })

  async function handleCreateGoal(data: CreateGoalForm) {
    await createGoal({
      title: data.title,
      desiredWeeklyFrequency: data.desiredWeeklyFrequency
    })

    queryClient.invalidateQueries({
      queryKey: ["summary"]
    })
    queryClient.invalidateQueries({
      queryKey: ["pending-goals"]
    })

    reset()
  }

  return (
    <DialogContent>
      <div className="flex flex-col gap-6 h-full">
        <div className="flex flex-col gap-3">
          <div className="flex items-center justify-between">
            <DialogTitle>Cadastrar Meta</DialogTitle>
            <DialogClose>
              <X className="size-5 text-zinc-600" />
            </DialogClose>
          </div>

          <DialogDescription>
            Adicione atividades que{" "}
            <span className="underline underline-offset-2">te fazem bem</span> e que você quer
            continuar praticando toda semana.
          </DialogDescription>
        </div>

        <form
          action=""
          className="flex flex-col justify-between flex-1"
          onSubmit={handleSubmit(handleCreateGoal)}
        >
          <div className="flex flex-col gap-6">
            <div className="flex flex-col gap-2">
              <Label htmlFor="title">Qual a atividade?</Label>
              <Input
                placeholder="Praticar exercicios, meditar, etc..."
                autoFocus
                id="title"
                {...register("title")}
              />

              {formState.errors.title && (
                <p className="text-red-400 text-sm">{formState.errors.title.message}</p>
              )}
            </div>

            <div className="flex flex-col gap-2">
              <Label htmlFor="">Quantas vezes na semana?</Label>
              <Controller
                control={control}
                name="desiredWeeklyFrequency"
                defaultValue={3}
                render={({ field }) => {
                  return (
                    <RadioGroup
                      onValueChange={field.onChange}
                      value={String(field.value)}
                    >
                      {daysOfWeek.map(day => {
                        return (
                          <RadioGroupItem
                            value={day.value}
                            key={day.value}
                          >
                            <RadioGroupIndicator />
                            <span className="text-zinc-300 text-sm font-medium leading-none">
                              {day.title}
                            </span>
                            <span className="text-lg leading-none">{day.emotion}</span>
                          </RadioGroupItem>
                        )
                      })}
                    </RadioGroup>
                  )
                }}
              />
            </div>
          </div>

          <div className="flex items-center gap-3">
            <DialogClose asChild>
              <Button
                className="flex-1"
                variant="secondary"
                type="button"
              >
                Fechar
              </Button>
            </DialogClose>
            <Button className="flex-1">Salvar</Button>
          </div>
        </form>
      </div>
    </DialogContent>
  )
}
