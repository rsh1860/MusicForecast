module.exports = {
  content: ['./src/**/*.{js,jsx,ts,tsx}'],
  theme: {
    extend: {
      'fontFamily': {
        'sigmar-one': ['Sigmar One', 'cursive'],
        'spoqa-hansans': ['Spoqa Han Sans Neo', 'sans-serif'],
      },
      'colors': {
        'slider-bg': '#C487F4',
        'slider-thumb': '#C487F4',
      },
      'custom-toast': 'position-absolute top-50px left-50',
      'width': {
        '1040': '1040px',
        '1200': '1200px',
        '400': '400px',
        '485': '485px',
        '500px': '500px',
        '700': '700px',
        '900': '900px',
      },
      'backgroundColor': {
        'gray-custom': '#B3B3B3',
        'gray-custom-2': '#332E30',
      },
    },
    borderWidth: {
      default: '1px',
      0: '0',
      2: '2px',
      4: '4px',
    },
    keyframes: {
      'spin-slow': {
        '0%': { transform: 'rotate(0deg)' },
        '100%': { transform: 'rotate(360deg)' },
      },
      'fadeInBottomRight-fast': {
        '0%': {
          transform: 'translateX(100%) translateY(100%) scale(0)',
          opacity: 0,
        },
        '100%': {
          transform: 'translateX(0) translateY(0) scale(1)',
          opacity: 1,
        },
      },
      'scale-anim': {
        '0%': { transform: 'scale(0)' },
        '100%': { transform: 'scale(1)' },
      },
      'slider': {
        '0%': {
          transform: 'translateX(-100%)',
        },
        '100%': {
          transform: 'translateX(0%)',
        },
      },
      'slider2': {
        '0%': {
          transform: 'translateX(-50%)',
        },
        '100%': {
          transform: 'translateX(50%)',
        },
      },
    },
    animation: {
      'spin-slow': 'spin-slow 5s linear infinite',
      'scale-anim': 'scale-anim 1s ease-in-out',
      'fadeIn': 'fadeIn 1s linear',
      'slideOutUp-fast': 'slideOutUp 1s',
      'fadeInBottomRight-fast': 'fadeInBottomRight 1s cubic-bezier(0.42, 0, 0.02, 0.96)',
      'bounceIn': 'bounceIn 1s linear',
      'pulse': 'pulse 1s ease-in-out infinite',
      'slider': 'slider 10s linear infinite ',
      'slider2': 'slider2 18s linear infinite ',
    },
  },
  plugins: [],
};
