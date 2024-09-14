import type { SummaryResponse } from "../types/summary-response"

export async function getSummary(): Promise<SummaryResponse> {
  const response = await fetch("http://localhost:8080/api/goals/summary")
  const data = await response.json()

  return data.summary
}
