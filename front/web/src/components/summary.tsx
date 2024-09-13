import { CheckCircle2, Plus } from "lucide-react"
import { Button } from "./ui/button"
import { DialogTrigger } from "./ui/dialog"
import { InOrbitIcon } from "./in-orbit-icon"
import { Progress, ProgressIndicator } from "./ui/progress-bar"
import { Separator } from "./ui/separator"
import { OutlineButton } from "./ui/outline-button"

export function Summary() {
  return (
    <div className="py-10 max-w-[30rem] px-5 mx-auto flex flex-col gap-6">
      <div className="flex items-center justify-between">
        <div className="flex items-center gap-3">
          {/* Quando a imagem é pequena podemos usar o proprio vetor */}
          {/* Ela não é carregada, pois está inserida dentro do html */}
          <InOrbitIcon />

          <span className="text-lg font-semibold">5 a 10 de Agosto</span>
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
          <ProgressIndicator style={{ width: "50%" }} />
        </Progress>

        <div className="flex items-center justify-between text-xs text-zinc-400">
          <span>
            Você completou <span className={"text-zinc-100"}>8</span> de{" "}
            <span className={"text-zinc-100"}>15</span> metas nessa semana.
          </span>
          <span>50%</span>
        </div>

        <Separator />

        {/* flex-wrap quebra a linha */}
        <div className="flex flex-wrap gap-3">
          <OutlineButton>
            <Plus className="size-4" />
            Meditar
          </OutlineButton>

          <OutlineButton>
            <Plus className="size-4" />
            Nadar
          </OutlineButton>
        </div>

        <div className="flex flex-col gap-6">
          <h2 className="text-xl font-medium">Sua Semana</h2>

          <div className="flex flex-col gap-4">
            <h3 className="font-medium">
              Domingo <span className="text-zinc-400 text-xs">(10 de Agosto)</span>
            </h3>

            <ul className="flex flex-col gap-3">
              <li className="flex items-center gap-2">
                <CheckCircle2 className="size-4 text-pink-500" />
                <span className="text-sm text-zinc-400">
                  Você completou <span className="text-zinc-100">Acordar cedo</span> às{" "}
                  <span className="text-zinc-100">08:13h</span>
                </span>
              </li>

              <li className="flex items-center gap-2">
                <CheckCircle2 className="size-4 text-pink-500" />
                <span className="text-sm text-zinc-400">
                  Você completou <span className="text-zinc-100">Acordar cedo</span> às{" "}
                  <span className="text-zinc-100">08:13h</span>
                </span>
              </li>
            </ul>
          </div>
        </div>
      </div>
    </div>
  )
}
