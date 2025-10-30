/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./index.html",
    "./src/**/*.{vue,js,ts,jsx,tsx}",
  ],
  important: true,
  theme: {
    container: {
      center: true,
      padding: {
        DEFAULT: '1rem',
        sm: '1.25rem',
        lg: '2rem',
        xl: '2.5rem',
      }
    },
    extend: {
      colors: {
        'primary': '#639FFF',
        'primary-dark': '#4A7DD8',
      }
    },
  },
  safelist: [
    // text colors used dynamically
    'text-red-600',
    'text-green-600',
    'text-yellow-600',
    'text-red-700',
    'text-green-700',
    'text-yellow-700',
    // background helpers used dynamically
    'bg-red-100',
    'bg-green-100',
    'bg-yellow-50',
    'bg-white/90',
  ],
  plugins: [],
}