import React, { useState } from 'react';
import { useDispatch } from 'react-redux';
import axios from 'axios';
import { songlistInfo } from '../../redux/slice/SonglistsSlice';
import search from '../../assets/images/search.png';

const SearchInput = () => {
  const headers = {
    'Access-Control-Allow-Origin': `${process.env.REACT_APP_FE_HEADER_URL}`,
  };
  const [keyword, setKeyword] = useState('');
  const dispatch = useDispatch();

  const handleSearch = () => {
    axios
      .get(`${process.env.REACT_APP_BE_API_URL}/song/search?keyword=${keyword}&song=10`, {
        headers,
      })
      .then((res) => {
        dispatch(songlistInfo(res.data));
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
    <div className="flex justify-around my-6">
      {/* 검색칸 */}
      <div className="w-[528px] h-[50px] flex items-center bg-[#faf7f7e8] rounded-3xl  ">
        <input
          type="text"
          placeholder="노래 제목을 입력, 영어로 검색시 정확도가 높아요!"
          className="w-[468px] h-[50px] pl-4 bg-[#faf7f7e8] rounded-3xl border border-gray-500"
          value={keyword}
          onChange={(e) => setKeyword(e.target.value)}
          onKeyPress={handleKeyPress} // 입력 값이 변경될 때마다 키워드 상태를 업데이트합니다.
        />
        <button
          className="w-[30px] h-[30px] mr-4"
          onClick={handleSearch} // 검색 버튼 클릭 시 검색 함수를 호출합니다.
        >
          <img src={search} alt="검색" />
        </button>
      </div>
    </div>
  );
};

export default SearchInput;
