module.exports = {
  // ...
  module: {
    rules: [
      // ...
      {
        test: /\.(jpe?g|png|gif|svg)$/,
        loader: 'file-loader',
      },
    ],
  },
  // ...
};
