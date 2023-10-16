import { useNavigate } from 'react-router-dom';
import Header from '../../components/Header';
import Wrapper from '../../components/Wrapper/Wrapper';

const MUBTIMain = () => {
  const navigate = useNavigate();

  const handleGoSurvey = () => {
    navigate('../mubti/1');
  };
  return (
    <>
      <div className="bg-[#35435e]">
        <Header />
        <Wrapper>
          <div
            style={{
              height: '100vh',
              background: '#35435e',
            }}
            className="flex flex-col justify-center items-center"
          >
            <img
              src="https://user-images.githubusercontent.com/101685650/268509128-d79277d1-477d-410c-b6b0-0b68fc92d7a0.gif"
              alt="MUBTI"
              className="h-[550px]"
            />
            <div className="flex flex-col justify-center items-center text-white">
              <h1 className="text-4xl font-bold">
                당신의 음악 취향, 상황, 기분에 맞춰서 음악을 추천 해 드려요 !!{' '}
              </h1>
              <h2 className="text-2xl font-bold my-8">Shall We MUBTI !?</h2>
              <button
                onClick={handleGoSurvey}
                // href="./1"
                className="w-[200px] h-[50px] bg-[#efd0a0] inline-flex justify-center items-center text-black font-bold mb-4 rounded-full hover:translate-y-[-10px] transition duration-300 ease-in-out"
              >
                MUBTI 바로가기
              </button>
            </div>
          </div>
        </Wrapper>
      </div>
    </>
  );
};

export default MUBTIMain;
