import { useRoutes } from "react-router-dom";
import Footer from "./components/Footer";
import { routes } from "./routes"; // or use Vite's alias to simplify import path for nested components
import { Reset } from "styled-reset";

const App = () => {
  const element = useRoutes(routes);
  return (
    <>
      {element}
      <Footer />
      <Reset />
    </>
  );
};

export default App;
