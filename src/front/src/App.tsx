import { useRoutes } from "react-router-dom";
import { routes } from "./routes"; // or use Vite's alias to simplify import path for nested components
import { Reset } from "styled-reset";
import Footer from "./components/Footer";
import {
  useQuery,
  useMutation,
  useQueryClient,
  QueryClient,
  QueryClientProvider,
} from "@tanstack/react-query";

const queryClient = new QueryClient();

const App = () => {
  const element = useRoutes(routes);
  return (
    <>
      <QueryClientProvider client={queryClient}>
        {element}
        <Footer />
        <Reset />
      </QueryClientProvider>
    </>
  );
};

export default App;
