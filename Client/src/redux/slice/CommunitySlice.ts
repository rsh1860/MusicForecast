import { createSlice } from '@reduxjs/toolkit';

const CommunitySlice = createSlice({
  name: 'community',
  initialState: {
    currentPage: '',
  },
  reducers: {
    setCurrentPage: (state, action) => {
      state.currentPage = action.payload;
    },
  },
});

export default CommunitySlice.reducer;

export const { setCurrentPage } = CommunitySlice.actions;
