/** @type {import('tailwindcss').Config} */
export default {
  content: [
    './index.html',
    './src/**/*.{vue,js,ts,jsx,tsx}',
    './node_modules/vue-tailwind-datepicker/**/*.js',
  ],
  theme: {
    extend: {
      colors: {
        'app-gray-one': '#09090B',
        'app-gray-two': '#18181B',
        'app-gray-three': '#3A3A3D',
        'app-green': '#22C55E',
        'app-blue': '#3B82F4',
        'app-red': '#EF4444',
        'app-gray': '#79797a',
      },
      keyframes: {
        fadeIn: {
          '0%': { opacity: 0 },
          '100%': { opacity: 1 },
        },
      },
      animation: {
        'fade-in': 'fadeIn 0.5s ease-in-out',
      },
    },
    fontFamily: {
      Roboto: ['Roboto, sans-serif'],
    },
    screens: {
      sm: '640px',
      md: '768px',
    },
  },
  plugins: [require('tailwind-scrollbar')],
}
