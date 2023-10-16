module.exports = {
  parser: '@typescript-eslint/parser',
  plugins: ['import', '@typescript-eslint', 'prettier'],
  extends: [
    'airbnb-base',
    'plugin:import/errors',
    'plugin:import/warnings',
    'plugin:@typescript-eslint/recommended',
    'plugin:prettier/recommended',
    'eslint:recommended',
  ],
  parserOptions: {
    ecmaVersion: 6,
    sourceType: 'module',
  },
  env: {
    browser: true,
    node: true,
  },
  ignorePatterns: ['node_modules/', 'tsconfig.json'],
  rules: {
    // console 사용 허용
    '@typescript-eslint/no-non-null-assertion': 'off',
    'import/no-extraneous-dependencies': 'off',
    'import/prefer-default-export': 'off',
    'no-param-reassign': 'off',
    'no-explicit-any': 'off',
    'no-console': 'off',
    'prettier/prettier': [
      'error',
      {
        // 운영체제 별로 줄 바꿈 설정
        endOfLine: 'auto',
      },
    ],
    'import/extensions': [
      'error',
      'ignorePackages',
      {
        js: 'never',
        jsx: 'never',
        ts: 'never',
        tsx: 'never',
      },
    ],
    // 클래스 멤버(변수, 함수)는 무조건 한 줄 씩 띄워져야 함
    'lines-between-class-members': ['error', 'always', { exceptAfterSingleLine: true }],
  },
  settings: {
    'import/resolver': {
      typescript: {},
      node: {
        extensions: ['.js', '.jsx', '.ts', '.tsx'],
      },
    },
  },
};
