import { CheckCircle2, Plus } from "lucide-react"
import { Button } from "./ui/button"
import { DialogTrigger } from "./ui/dialog"
import { InOrbitIcon } from "./in-orbit-icon"
import { Progress, ProgressIndicator } from "./ui/progress-bar"
import { Separator } from "./ui/separator"
import { OutlineButton } from "./ui/outline-button"
import { useQuery } from "@tanstack/react-query"
import { getSummary } from "../http/get-summary"
import dayjs from "dayjs"
import ptBR from "dayjs/locale/pt-BR"
import { PendingGoals } from "./pending-goals"

dayjs.locale(ptBR)

export function Summary() {
  const { data } = useQuery({
    queryKey: ["summary"],
    queryFn: getSummary,
    // evita fazermos 2x a mesma requisição o tempo todo
    // somente quando o tempo do cache interno ultrapassar
    // ele sabe que a requisição já foi feita por causa da queryKey
    staleTime: 1000 * 60 // 60 segundos
  })

  if (!data) {
    return null
  }

  const firstDayOfWeek = dayjs().startOf("week").format("D MMM")
  const lastDayOfWeek = dayjs().endOf("week").format("D MMM")

  const completedPercentage = Math.round((data.completed * 100) / data.total)

  return (
    <div className="py-10 max-w-[30rem] px-5 mx-auto flex flex-col gap-6">
      <div className="flex items-center justify-between">
        <div className="flex items-center gap-3">
          {/* Quando a imagem é pequena podemos usar o proprio vetor */}
          {/* Ela não é carregada, pois está inserida dentro do html */}
          <InOrbitIcon />

          <span className="text-lg font-semibold capitalize">
            {firstDayOfWeek} - {lastDayOfWeek}
          </span>
        </div>

        <DialogTrigger asChild>
          <Button size="sm">
            <Plus className="size-4" />
            Cadastrar Meta
          </Button>
        </DialogTrigger>
      </div>

      <div className="flex flex-col gap-3">
        <Progress
          value={8}
          max={15}
        >
          <ProgressIndicator style={{ width: `${completedPercentage}%` }} />
        </Progress>

        <div className="flex items-center justify-between text-xs text-zinc-400">
          <span>
            Você completou <span className={"text-zinc-100"}>{data.completed}</span> de{" "}
            <span className={"text-zinc-100"}>{data.total}</span> metas nessa semana.
          </span>
          <span>{completedPercentage}%</span>
        </div>

        <Separator />

        <PendingGoals />

        <div className="flex flex-col gap-6">
          <h2 className="text-xl font-medium">Sua Semana</h2>

          {Object.entries(data.goalsPerDay).map(([date, goals]) => {
            const weekDay = dayjs(date).format("dddd")
            const formattedDate = dayjs(date).format("D [de] MMMM")

            return (
              <div
                className="flex flex-col gap-4"
                key={date}
              >
                <h3 className="font-medium">
                  <span className="capitalize">{weekDay}</span>{" "}
                  <span className="text-zinc-400 text-xs">({formattedDate})</span>
                </h3>

                <ul className="flex flex-col gap-3">
                  {goals.map(goal => {
                    const formattedHour = dayjs(goal.completedAt).format("HH:mm[h]")

                    return (
                      <li
                        className="flex items-center gap-2"
                        key={goal.id}
                      >
                        <CheckCircle2 className="size-4 text-pink-500" />
                        <span className="text-sm text-zinc-400">
                          Você completou <span className="text-zinc-100">{goal.title}</span> às{" "}
                          <span className="text-zinc-100">{formattedHour}</span>
                        </span>
                      </li>
                    )
                  })}
                </ul>
              </div>
            )
          })}
        </div>
      </div>
    </div>
  )
}
