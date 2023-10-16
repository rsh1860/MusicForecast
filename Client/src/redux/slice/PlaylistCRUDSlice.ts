import { createSlice } from '@reduxjs/toolkit';

const PlaylistCRUDSlice = createSlice({
  name: 'playlistCRUD',
  initialState: [],
  reducers: {
    createPlaylist: (state, action) => {
      state.push(action.payload);
    },
  },
});

export default PlaylistCRUDSlice;

export const { createPlaylist } = PlaylistCRUDSlice.actions;
