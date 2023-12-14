module.exports = {
    env: {
      node: true,
      browser: true,
      es2021: true,
    },
    ignorePatterns: ["node_modules/"],
    settings: {
      react: {
        version: "detect",
      },
      "import/resolver": {
        node: {
          paths: ["src"],
        },
      },
    },
    extends: ["plugin:react/recommended", "prettier", "eslint:recommended"],
    parserOptions: {
      ecmaFeatures: {
        jsx: true,
      },
      ecmaVersion: 12,
      sourceType: "module",
    },
    plugins: ["react", "prettier"],
    rules: {
      "prettier/prettier": ["warn"],
      "react/prop-types": "off",
      "react/no-unescaped-entities": 0,
    },
  };
  