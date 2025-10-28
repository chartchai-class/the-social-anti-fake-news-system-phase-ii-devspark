/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{vue,js,ts,jsx,tsx}"
  ],
  theme: {
    extend: {
      colors: {
        primary: '#639FFF',
        'primary-dark': '#4A86E6',
        'primary-light': '#8BB8FF',
      }
    },
  },
  plugins: [],
}
