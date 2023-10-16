import { useSelector, useDispatch } from 'react-redux';
import { RootState } from 'src/redux/store';
import axios from 'axios';
import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import playlistdisc from '../../../assets/images/playlistdisc.png';
import { serveyResult } from '../../../redux/slice/MUBTISlice';

const SurveyLoading = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const [showButton, setShowButton] = useState(false);

  const handleNavigate = () => {
    navigate('../mubti/result');
  };

  const a = useSelector((state: RootState) => state.mubti.serveyA);
  const b = useSelector((state: RootState) => state.mubti.serveyB);

  const headers = {
    'Access-Control-Allow-Origin': `${process.env.REACT_APP_FE_HEADER_URL}`,
  };

  useEffect(() => {
    axios
      .get(`${process.env.REACT_APP_BE_API_URL}/mubti/result?page=1&size=10&a=${a}&b=${b}`, {
        headers,
      })
      .then((res) => {
        dispatch(serveyResult(res.data.data));
        setTimeout(() => {
          setShowButton(true);
        }, 1000); // 1초(1000ms) 딜레이
      })
      .catch((err) => {
        console.log(err);
      });
  }, []);

  return (
    <div
      style={{
        height: '100vh',
        background: '#35435e',
      }}
      className="flex flex-col justify-center items-center"
    >
      {showButton ? null : (
        <div className="flex flex-row justify-between items-center w-[250px] ">
          <button
            onClick={() => navigate('../mubti/1')}
            className="w-[20px] h-[20px] bg-[#d9d9d9] rounded-full "
          ></button>
          <button
            onClick={() => navigate(-1)}
            className="w-[20px] h-[20px] bg-[#d9d9d9] rounded-full"
          ></button>
          <button className="w-[20px] h-[20px] bg-[#797676] rounded-full border-2 border-red-100 "></button>
          <button className="w-[20px] h-[20px] bg-[#d9d9d9] rounded-full"></button>
        </div>
      )}

      {showButton ? (
        <button
          onClick={handleNavigate}
          // href="./result"
          className="mt-12 w-[180px] h-[50px] inline-flex justify-center items-center rounded-2xl bg-[#EFD0A0] ml-6 text-xl hover:translate-y-[-10px] transition duration-300 ease-in-out"
        >
          결과보기
        </button>
      ) : (
        <div className="flex flex-col items-center justify-center ">
          <h1 className="my-12 font-['Anton-Regular'] font-semibold text-[#d9d9d9] text-2xl">
            앞의 검사 결과를 통해 본인에 맞는 노래추천이 진행중입니다...
          </h1>
          <img
            className="w-[400px] h-[400px] ml-6 animate-spin-slow"
            src={playlistdisc}
            alt="플레이리스트디스크"
          />
        </div>
      )}
    </div>
  );
};

export default SurveyLoading;
