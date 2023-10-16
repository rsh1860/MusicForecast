import { useDispatch } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { setServeyB } from '../../../redux/slice/MUBTISlice';

const SurveyMood = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const handleSurvey = (b: number) => {
    dispatch(setServeyB(b));
    navigate('../mubti/loading');
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
        <button
          onClick={() => navigate(-1)}
          className="w-[20px] h-[20px] bg-[#d9d9d9] rounded-full "
        ></button>
        <button className="w-[20px] h-[20px] bg-[#797676] rounded-full border-2 border-red-100 "></button>
        <button
          onClick={() => alert('선택지를 선택하세요')}
          className="w-[20px] h-[20px] bg-[#d9d9d9] rounded-full"
        ></button>
        <button
          onClick={() => alert('선택지를 선택하세요')}
          className="w-[20px] h-[20px] bg-[#d9d9d9] rounded-full"
        ></button>
      </div>
      <h2 className="text-white text-2xl font-bold my-8">본인의 기분을 골라주세요!</h2>
      <button
        onClick={() => handleSurvey(1)}
        // href="./loading"
        className="inline-flex justify-center items-center w-[450px] h-[50px] bg-[#D9D9D9] text-black font-bold my-5 rounded-full hover:bg-[#EFD0A0]"
      >
        슬픔, 우울, 쉬고싶음의 기분
      </button>
      <button
        onClick={() => handleSurvey(2)}
        // href="./loading"
        className="inline-flex justify-center items-center w-[450px] h-[50px] bg-[#D9D9D9] text-black font-bold my-5 rounded-full hover:bg-[#EFD0A0]"
      >
        화가나고, 예민하며 짜증나는 기분
      </button>
      <button
        onClick={() => handleSurvey(3)}
        // href="./loading"
        className="inline-flex justify-center items-center w-[450px] h-[50px] bg-[#D9D9D9] text-black font-bold my-5 rounded-full hover:bg-[#EFD0A0]"
      >
        즐겁고, 신나고, 흥이많은 기분
      </button>
      <button
        onClick={() => handleSurvey(4)}
        // href="./loading"
        className="inline-flex justify-center items-center w-[450px] h-[50px] bg-[#D9D9D9] text-black font-bold my-5 rounded-full hover:bg-[#EFD0A0]"
      >
        그저 그런 기분 (이도저도 아님)
      </button>
    </div>
  );
};

export default SurveyMood;
