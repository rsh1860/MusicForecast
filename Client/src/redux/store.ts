import { combineReducers, configureStore } from '@reduxjs/toolkit';
import { persistReducer, persistStore } from 'redux-persist';
import storage from 'redux-persist/lib/storage';
import ModalSlice from './slice/ModalSlice';
import PlaylistsSlice from './slice/PlaylistsSlice';
import LoginReducer from './slice/LoginSlice';
import PlaylistCRUDSlice from './slice/PlaylistCRUDSlice';
import SongListsSlice from './slice/SonglistsSlice';
import WeatherSlice from './slice/WeatherSlice';
import MUBTISlice from './slice/MUBTISlice';

const rootreducer = combineReducers({
  modal: ModalSlice.reducer,
  playlists: PlaylistsSlice.reducer,
  playlistCRUD: PlaylistCRUDSlice.reducer,
  login: LoginReducer,
  songlists: SongListsSlice.reducer,
  weather: WeatherSlice.reducer,
  mubti: MUBTISlice.reducer,
});

// config 작성
const persistConfig = {
  key: 'root', // localStorage key
  storage, // localStorage
  whitelist: ['login'], // target (reducer name)
};

const persistedReducer = persistReducer(persistConfig, rootreducer);

const store = configureStore({
  reducer: persistedReducer,
});

export type RootState = ReturnType<typeof store.getState>;

export const persistor = persistStore(store);

export default store;
