export interface SummaryResponse {
  completed: number
  total: number
  goalsPerDay: GoalsPerDay
}

type GoalsPerDay = Record<string, GoalCompletionSummary[]>

interface GoalCompletionSummary {
  id: string
  title: string
  completedAt: string
}
