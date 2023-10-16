import { createSlice } from '@reduxjs/toolkit';

const SonglistsSlice = createSlice({
  name: 'songlists',
  initialState: {
    value: [],
    songInfo: {
      songId: 0,
      imageUrl: '',
      title: '',
      artistName: '',
      albumName: '',
    },
    songInfoList: [],
    deletedSongs: [],
    addSong: {},
  },
  reducers: {
    songlistInfo: (state, action) => {
      state.value = action.payload;
    },
    selectedSonglist: (state, action) => {
      state.songInfo = action.payload;
    },
    addSongToPlaylist: (state, action) => {
      state.addSong = action.payload;
    },
    setSelectedSongs: (state, action) => {
      state.songInfoList = action.payload; // 선택된 노래 배열을 업데이트합니다
    },
    deletedSongs: (state, action) => {
      state.deletedSongs.push(action.payload); // 삭제된 노래 배열을 업데이트합니다
    },
  },
});

export default SonglistsSlice;

export const { songlistInfo, selectedSonglist, addSongToPlaylist, setSelectedSongs, deletedSongs } =
  SonglistsSlice.actions;
