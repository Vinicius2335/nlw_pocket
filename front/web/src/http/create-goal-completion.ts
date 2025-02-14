export async function createGoalCompletion(goalId: string) {
  await fetch("http://localhost:8080/api/goals/completions", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ goalId })
  })
}
