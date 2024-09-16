import { Plus } from "lucide-react"
import { OutlineButton } from "./ui/outline-button"
import { useQuery } from "@tanstack/react-query"
import { getPendingGoals } from "../http/get-pending-goals"
import { createGoalCompletion } from "../http/create-goal-completion"
import { toast } from "sonner"
import { queryClient } from "../libs/query-client"

export function PendingGoals() {
  const { data } = useQuery({
    queryKey: ["pending-goals"],
    queryFn: getPendingGoals
  })

  if (!data) {
    return null
  }

  async function handleCompletedGoal(goalId: string) {
    try {
      await createGoalCompletion(goalId)

      // refaz o carregamento das querrys relacionados a queryKey
      queryClient.invalidateQueries({
        queryKey: ["summary"]
      })
      queryClient.invalidateQueries({
        queryKey: ["pending-goals"]
      })
    } catch (error) {
      toast.error("Não foi possível completar a meta, tente novamente mais tarde!")
    }
  }

  return (
    // {/* flex-wrap quebra a linha */}
    <div className="flex flex-wrap gap-3">
      {data.map(goal => {
        return (
          <OutlineButton
            key={goal.id}
            disabled={goal.completionCount >= goal.desiredWeeklyFrequency}
            onClick={() => handleCompletedGoal(goal.id)}
          >
            <Plus className="size-4" />
            {goal.title}
          </OutlineButton>
        )
      })}
    </div>
  )
}
