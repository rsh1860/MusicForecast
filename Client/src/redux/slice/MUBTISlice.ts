import { createSlice } from '@reduxjs/toolkit';

const MUBTISlice = createSlice({
  name: 'MUBTI',
  initialState: {
    serveyA: 0,
    serveyB: 0,
    Result: [],
  },
  reducers: {
    setServeyA: (state, action) => {
      state.serveyA = action.payload;
    },
    setServeyB: (state, action) => {
      state.serveyB = action.payload;
    },
    serveyResult: (state, action) => {
      state.Result = action.payload;
    },
  },
});

export default MUBTISlice;

export const { setServeyA, setServeyB, serveyResult } = MUBTISlice.actions;
