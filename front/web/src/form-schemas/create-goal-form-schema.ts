import { z } from "zod"

export const createGoalFormSchema = z.object({
  title: z.string().min(1, "Informe a atividade que deseja realizar"),
  // o value do radioGroup precisa ser uma string
  // coerce > converte uma string em number
  desiredWeeklyFrequency: z.coerce.number().min(1).max(7)
})

export type CreateGoalForm = z.infer<typeof createGoalFormSchema>
