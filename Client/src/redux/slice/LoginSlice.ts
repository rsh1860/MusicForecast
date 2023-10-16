import { createSlice } from '@reduxjs/toolkit';

const LoginSlice = createSlice({
  name: 'login',
  initialState: {
    accessToken: '',
    refreshToken: '',
    loginState: false,
    memberid: 0,
    nickname: '',
  },
  reducers: {
    setAccessToken: (state, action) => {
      state.accessToken = action.payload;
    },
    setRefreshToken: (state, action) => {
      state.refreshToken = action.payload;
    },
    setLoginState: (state, action) => {
      state.loginState = action.payload;
    },
    setMemberID: (state, action) => {
      state.memberid = action.payload;
    },
    setNickname: (state, action) => {
      state.nickname = action.payload;
    },
    logout: (state) => {
      state.accessToken = '';
      state.refreshToken = '';
      state.loginState = false;
    },
  },
});

export default LoginSlice.reducer;

export const { setAccessToken, setRefreshToken, setLoginState, logout, setMemberID, setNickname } =
  LoginSlice.actions;
