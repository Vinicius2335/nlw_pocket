import { useQuery } from "@tanstack/react-query"
import { CreateGoal } from "./components/create-goal"
import { EmptyGoals } from "./components/empty-goals"
import { Summary } from "./components/summary"
import { Dialog } from "./components/ui/dialog"
import { getSummary } from "./http/get-summary"
import { Toaster } from "sonner"

export function App() {
  const { data } = useQuery({
    queryKey: ["summary"],
    queryFn: getSummary,
    // cache interno
    // passamo em ms quanto tempo o dado ainda não será obsoleto
    staleTime: 1000 * 60 // 60 segundos
  })

  return (
    <>
      <Toaster
        theme="dark"
        position="top-center"
        richColors
      />

      <Dialog>
        {data?.total && data.total > 0 ? <Summary /> : <EmptyGoals />}
        <CreateGoal />
      </Dialog>
    </>
  )
}
