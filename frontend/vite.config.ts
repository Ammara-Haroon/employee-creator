import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [react()],
  //base: "/employee-creator",
  server: {
    host: true,
  },
  base: "/employee-creator",
});