import { Plus } from "lucide-react"
import { OutlineButton } from "./ui/outline-button"
import { useQuery, useQueryClient } from "@tanstack/react-query"
import { getPendingGoals } from "../http/get-pending-goals"
import { createGoalCompletion } from "../http/create-goal-completion"

export function PendingGoals() {
  const queryClient = useQueryClient()

  const { data } = useQuery({
    queryKey: ["pending-goals"],
    queryFn: getPendingGoals
  })

  if (!data) {
    return null
  }

  async function handleCompletedGoal(goalId: string) {
    await createGoalCompletion(goalId)

    // refaz o carregamento das querrys relacionados a queryKey
    queryClient.invalidateQueries({
      queryKey: ["summary"]
    })
    queryClient.invalidateQueries({
      queryKey: ["pending-goals"]
    })
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
