var path = require('path');
var CopyWebpackPlugin = require('copy-webpack-plugin');

module.exports = {
    entry: {
       app: './src/main/js/app.js',
       process: './src/main/js/entries/process.js'
    },
    mode: 'development',
    devtool: 'sourcemaps',
    cache: true,
    output: {
        path: __dirname,
        filename: './src/main/resources/static/js/[name].bundle.js'
    },
    plugins: [
        new CopyWebpackPlugin([
            {
                from:'node_modules/materialize-css/dist/css/materialize.css',
                to:'./src/main/resources/static/css/main.bundle.css'
            }
        ])
    ],
    module: {

        rules: [
            {
                test: path.join(__dirname, '.'),
                exclude: /(node_modules)/,
                use: [
                    {
                        loader: 'babel-loader',
                        query: {
                            cacheDirectory: true,
                            presets: ['es2016', 'react']
                        }
                    }
                ]
            },
            {
                test: /\.css$/,
                use: [ 'style-loader', 'css-loader' ]
            }
        ]
    }
};