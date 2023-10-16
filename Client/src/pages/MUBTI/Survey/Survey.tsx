import { useDispatch } from 'react-redux';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { setServeyA, serveyResult } from '../../../redux/slice/MUBTISlice';

const Survey = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();

  dispatch(serveyResult([]));

  const handleSurvey = (a: number) => {
    dispatch(setServeyA(a));
    switch (a) {
      case 1:
        navigate('../mubti/mood');
        break;

      case 2:
        navigate('../mubti/situation');
        break;

      case 3:
        navigate('../mubti/liking');
        break;

      default:
        break;
    }
  };

  const handleRecommendYoutube = () => {
    axios
      .get(`${process.env.REACT_APP_BE_API_URL}/youtuber?q=5`)
      .then((res) => {
        console.log(res.data);
        navigate('../mubti/youtube');
      })
      .catch((err) => {
        console.log(err);
      });
  };

  return (
    <div
      style={{
        height: '100vh',
        background: '#35435e',
      }}
      className="flex flex-col justify-center items-center"
    >
      <div className="flex flex-row justify-between items-center w-[250px] ">
        <button className="w-[20px] h-[20px] bg-[#797676] rounded-full border-2 border-red-100"></button>
        <button
          onClick={() => alert('선택지를 선택하세요')}
          className="w-[20px] h-[20px] bg-[#d9d9d9] rounded-full "
        ></button>
        <button
          onClick={() => alert('선택지를 선택하세요')}
          className="w-[20px] h-[20px] bg-[#d9d9d9] rounded-full"
        ></button>
        <button
          onClick={() => alert('선택지를 선택하세요')}
          className="w-[20px] h-[20px] bg-[#d9d9d9] rounded-full"
        ></button>
      </div>
      <h2 className="text-white text-2xl font-bold my-8">
        간단한 설문조사를 진행할 예정입니다. 본인의 현재 상황을 알려주세요 !
      </h2>
      <button
        onClick={() => handleSurvey(1)}
        // href="./mood"
        className="inline-flex justify-center items-center w-[450px] h-[50px] bg-[#D9D9D9] text-black font-bold my-5 rounded-full hover:bg-[#EFD0A0]"
      >
        기분
      </button>
      <button
        onClick={() => handleSurvey(2)}
        // href="./situation"
        className="inline-flex justify-center items-center w-[450px] h-[50px] bg-[#D9D9D9] text-black font-bold my-5 rounded-full hover:bg-[#EFD0A0]"
      >
        상황 (운동,공부,잠)
      </button>
      <button
        onClick={() => handleSurvey(3)}
        // href="./liking"
        className="inline-flex justify-center items-center w-[450px] h-[50px] bg-[#D9D9D9] text-black font-bold my-5 rounded-full hover:bg-[#EFD0A0]"
      >
        노래 분위기
      </button>
      <button
        onClick={() => handleRecommendYoutube()}
        // href="./result"
        className="inline-flex justify-center items-center w-[450px] h-[50px] bg-[#D9D9D9] text-black font-bold my-5 rounded-full hover:bg-[#EFD0A0]"
      >
        유튜버 플레이리스트
      </button>
    </div>
  );
};

export default Survey;
