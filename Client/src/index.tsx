import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import { Provider } from 'react-redux';
import { PersistGate } from 'redux-persist/integration/react';
import { GoogleOAuthProvider } from '@react-oauth/google';
import axios from 'axios';
import { ToastContainer } from 'react-toastify';
import { BrowserRouter } from 'react-router-dom';
import store, { persistor } from './redux/store';
import reportWebVitals from './reportWebVitals';
import App from './App';

axios.defaults.withCredentials = true;

const root = ReactDOM.createRoot(document.getElementById('root')!);
root.render(
  <>
    <Provider store={store}>
      <PersistGate persistor={persistor}>
        <GoogleOAuthProvider
          clientId={process.env.REACT_APP_GOOGLE_AUTH_CLIENT_ID}
          onScriptLoadError={() => console.log('실패')}
        >
          <ToastContainer />
          <BrowserRouter>
            <App />
          </BrowserRouter>
        </GoogleOAuthProvider>
      </PersistGate>
    </Provider>
  </>
);

reportWebVitals();
