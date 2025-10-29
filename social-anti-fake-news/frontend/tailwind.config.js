/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./index.html",
    "./src/**/*.{vue,js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        'primary': '#639FFF',
        'primary-dark': '#4A7DD8',
      }
    },
  },
  plugins: [],
}