import axios from 'axios';
import React, { useState } from 'react';
import { useDispatch } from 'react-redux';
import { searchedPlaylist } from 'src/redux/slice/PlaylistsSlice';
import { openSearchedModal } from '../../redux/slice/ModalSlice';

const PlaylistsSearch = () => {
  const dispatch = useDispatch();
  const [keyword, setKeyword] = useState('');

  const handleSearch = () => {
    axios
      .get(`${process.env.REACT_APP_BE_API_URL}/playlist/search?page=1&size=10&keyword=${keyword}`)
      .then((res) => {
        dispatch(searchedPlaylist(res.data.data));
        dispatch(openSearchedModal());
      })
      .catch((err) => {
        console.log(err);
      });
  };

  const handleKeyPress = (event: React.KeyboardEvent<HTMLInputElement>) => {
    if (event.key === 'Enter') {
      handleSearch();
    }
  };

  return (
    <>
      <div className="flex mt-8">
        <input
          type="text"
          value={keyword}
          onChange={(e) => setKeyword(e.target.value)}
          onKeyPress={handleKeyPress}
          placeholder="   플레이리스트 이름을 입력해주세요"
          className="w-[40vh] h-[5vh] bg-[#444444d0] rounded-3xl border border-gray-500"
        ></input>
      </div>
    </>
  );
};

export default PlaylistsSearch;
