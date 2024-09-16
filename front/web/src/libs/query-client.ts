import { QueryCache, QueryClient } from "@tanstack/react-query"
import { toast } from "sonner"

export const queryClient = new QueryClient({
  queryCache: new QueryCache({
    onError: error => {
      console.log(error.message)
      return toast.error("Algo deu errado")
    }
  })
})
