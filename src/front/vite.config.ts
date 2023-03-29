import { reactRouterPlugin } from "vite-plugin-next-react-router";
import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    react({
      include: "**/*.tsx",
    }),
    reactRouterPlugin(),
  ],
});
