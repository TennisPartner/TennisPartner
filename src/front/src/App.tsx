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

import { useState } from "react";
import { userContext } from "./context/userContext";

const queryClient = new QueryClient();

const App = () => {
  const element = useRoutes(routes);

  const [user, setUser] = useState<string>("");

  return (
    <>
      <userContext.Provider value={{ user, setUser }}>
        <QueryClientProvider client={queryClient}>
          {element}
          <Footer />
          <Reset />
        </QueryClientProvider>
      </userContext.Provider>
    </>
  );
};

export default App;
