import { Plus } from "lucide-react"
import letsStart from "../assets/lets-start-illustration.svg"
import logo from "../assets/logo-in.orbit.svg"
import { Button } from "./ui/button"
import { DialogTrigger } from "./ui/dialog"

export function EmptyGoals() {
  return (
    <div className="h-screen flex flex-col items-center justify-center gap-8">
      {/* Lazy Load */}
      <img
        src={logo}
        alt="in.orbit"
      />

      <img
        src={letsStart}
        alt="lets start"
      />

      <p className="text-zinc-300 leading-relaxed max-w-80 text-center">
        Você ainda não cadastrou nenhuma meta, que tal{" "}
        <span className="underline underline-offset-2">cadastrar um</span> agora mesmo?
      </p>

      {/* Sem o 'asChild' acaba criando 2 botoes pq o DialogTrigger é um botão tbm */}
      <DialogTrigger asChild>
        <Button>
          <Plus className="size-4" />
          Cadastrar Meta
        </Button>
      </DialogTrigger>
    </div>
  )
}
