/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        lineColor: '#102639',
      },
      backgroundImage: {
          'mp_activity_background': "url('/src/assets/mp_activity_background.png')",
      }
    },
  },
  plugins: [],
}

