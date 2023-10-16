import { useDispatch } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { setServeyB } from '../../../redux/slice/MUBTISlice';

const SurveySituation = () => {
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
      <h2 className="text-white text-2xl font-bold my-8">본인의 상황을 골라주세요!!</h2>
      <button
        onClick={() => handleSurvey(1)}
        className="inline-flex justify-center items-center w-[450px] h-[50px] bg-[#D9D9D9] text-black font-bold my-5 rounded-full hover:bg-[#EFD0A0]"
      >
        운동
      </button>
      <button
        onClick={() => handleSurvey(2)}
        className="inline-flex justify-center items-center w-[450px] h-[50px] bg-[#D9D9D9] text-black font-bold my-5 rounded-full hover:bg-[#EFD0A0]"
      >
        출퇴근, 등하교길
      </button>
      <button
        onClick={() => handleSurvey(3)}
        className="inline-flex justify-center items-center w-[450px] h-[50px] bg-[#D9D9D9] text-black font-bold my-5 rounded-full hover:bg-[#EFD0A0]"
      >
        공부, 독서
      </button>
      <button
        onClick={() => handleSurvey(4)}
        className="inline-flex justify-center items-center w-[450px] h-[50px] bg-[#D9D9D9] text-black font-bold my-5 rounded-full hover:bg-[#EFD0A0]"
      >
        랜덤
      </button>
    </div>
  );
};

export default SurveySituation;
