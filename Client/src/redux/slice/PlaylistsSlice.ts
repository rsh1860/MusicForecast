import { createSlice } from '@reduxjs/toolkit';

const PlaylistsSlice = createSlice({
  name: 'playlists',
  initialState: {
    value: [],
    myPlaylist: [],
    myPlaylistDetail: [],
    detailInfo: [],
    detailData: {
      like: 0,
      view: 0,
      public: true,
      memberId: 0,
      playlistId: 0,
    },
    searchedPlaylist: [],
    pliDetailInfo: [],
    selectedPlaylistId: null,
    selectedMemberId: null,
    playlistTitle: '',
  },
  reducers: {
    playlistInfo: (state, action) => {
      state.value = action.payload;
    },
    playlistDetail: (state, action) => {
      state.detailInfo = action.payload;
    },
    playlistData: (state, action) => {
      state.detailData = action.payload;
    },
    myPlaylist: (state, action) => {
      state.myPlaylist = action.payload;
    },
    myPlaylistDetail: (state, action) => {
      state.myPlaylistDetail = action.payload;
    },
    setDetailData: (state, action) => {
      state.detailData = action.payload;
    },
    setSelectedPlaylistId: (state, action) => {
      state.selectedPlaylistId = action.payload;
    },
    setSelectedMemberId: (state, action) => {
      state.selectedMemberId = action.payload;
    },
    setPlaylistTitle: (state, action) => {
      state.playlistTitle = action.payload;
    },
    searchedPlaylist: (state, action) => {
      state.searchedPlaylist = action.payload;
    },
  },
});

export default PlaylistsSlice;

export const {
  playlistInfo,
  playlistDetail,
  myPlaylist,
  myPlaylistDetail,
  playlistData,
  setSelectedPlaylistId,
  setSelectedMemberId,
  setDetailData,
  setPlaylistTitle,
  searchedPlaylist,
} = PlaylistsSlice.actions;
