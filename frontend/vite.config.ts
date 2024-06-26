import { defineConfig } from "vitest/config";
import react from "@vitejs/plugin-react";
import tailwindcss from "tailwindcss";
// <reference types="vitest" />

export default defineConfig({
  plugins: [react()],
  server: {
    host: true,
  },
  test: {
    globals: true,
    environment: "jsdom",
    setupFiles: "./config/setup.js",
  },
  css: {
    postcss: {
      plugins: [tailwindcss()],
    },
  },
});
