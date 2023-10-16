import { createSlice } from '@reduxjs/toolkit';

const ModalSlice = createSlice({
  name: 'modal',
  initialState: {
    isOpen: false,
    isDetailOpen: false,
    isMyShowAll: false,
    isToastOpen: false,
    isAlbumDetailOpen: false,
    isSongOpen: false,
    isSongAddOpen: false,
    isSearchedOpen: false,
    isRecommendOpen: false,
  },
  reducers: {
    openModal: (state) => {
      state.isOpen = true;
    },
    closeModal: (state) => {
      state.isOpen = false;
    },
    openDetailModal: (state) => {
      state.isDetailOpen = true;
    },
    closeDetailModal: (state) => {
      state.isDetailOpen = false;
    },
    openMyShowAll: (state) => {
      state.isMyShowAll = true;
    },
    closeShowAll: (state) => {
      state.isMyShowAll = false;
    },
    openToastModal: (state) => {
      state.isToastOpen = true;
    },
    closeToastModal: (state) => {
      state.isToastOpen = false;
    },
    openAlbumDetailModal: (state) => {
      state.isAlbumDetailOpen = true;
    },
    closeAlbumDetailModal: (state) => {
      state.isAlbumDetailOpen = false;
    },
    openSongLists: (state) => {
      state.isSongOpen = true;
    },
    closeSongLists: (state) => {
      state.isSongOpen = false;
    },
    openSongAddModal: (state) => {
      state.isSongAddOpen = true;
    },
    closeSongAddModal: (state) => {
      state.isSongAddOpen = false;
    },
    openSearchedModal: (state) => {
      state.isSearchedOpen = true;
    },
    closeSearchedModal: (state) => {
      state.isSearchedOpen = false;
    },
    openRecommendModal: (state) => {
      state.isRecommendOpen = true;
    },
    closeRecommendModal: (state) => {
      state.isRecommendOpen = false;
    },
  },
});

export default ModalSlice;

export const {
  openModal,
  closeModal,
  openDetailModal,
  closeDetailModal,
  openMyShowAll,
  closeShowAll,
  openToastModal,
  closeToastModal,
  openAlbumDetailModal,
  closeAlbumDetailModal,
  openSongLists,
  closeSongLists,
  openSongAddModal,
  closeSongAddModal,
  openSearchedModal,
  closeSearchedModal,
  openRecommendModal,
  closeRecommendModal,
} = ModalSlice.actions;
