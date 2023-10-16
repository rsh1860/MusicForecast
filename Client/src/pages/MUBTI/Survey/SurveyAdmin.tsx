import { useEffect, useState } from 'react';
import axios from 'axios';
import Header from '../../../components/Header';

const SurveyAdmin = () => {
  const [state, setState] = useState([]);

  useEffect(() => {
    axios
      .get(`${process.env.REACT_APP_BE_API_URL}/admin-suggest?q=12`)
      .then((res) => {
        setState(res.data.data);
      })
      .catch((err) => {
        console.log(err);
      });
  }, []);

  return (
    <div className="w-screen h-screen bg-[#35435e]">
      <Header />
      <div className="w-full flex flex-col justify-center items-center mt-20">
        <div className="flex flex-row justify-between items-center w-[250px] ">
          <button className="w-[20px] h-[20px] bg-[#d9d9d9] rounded-full"></button>
          <button className="w-[20px] h-[20px] bg-[#d9d9d9] rounded-full "></button>
          <button className="w-[20px] h-[20px] bg-[#d9d9d9] rounded-full"></button>
          <button className="w-[20px] h-[20px] bg-[#797676] rounded-full border-2 border-red-100"></button>
        </div>
        <h1 className="flex my-8 font-['Anton-Regular'] text-[#d9d9d9] text-2xl font-semibold">
          두구두구둑두구,,,, 담당자가 뽑은 노래입니다 !! 마음에 드시면 좋겠네요 ㅎㅎ ~!
        </h1>
        <div className="w-[70%] flex flex-col items-center">
          <h1 className="w-full flex justify-start my-8 ml-6 font-['Anton-Regular'] text-2xl text-[#d9d9d9] ">
            Recommend
          </h1>
          {state && (
            <div className="w-[100vh] justify-center flex overflow-x-hidden overflow-y-hidden">
              <div className="w-[280vh] flex flex-nowrap animate-slider2 relative right-200">
                {state.map((el) => (
                  <a
                    href={`${el.youtubeLink}`}
                    className="flex flex-col items-center justify-center mx-8 my-2 text-white hover:translate-y-[15px] transition duration-300 ease-in-out"
                  >
                    <div className="w-[15vh] flex flex-col items-center">
                      <img src={el.thumbnailLink} className="w-[15vh] h-[10vh] my-4" />
                      <p className="h-[50px] text-center ">{el.artist}</p>
                      <p className="h-[50px] text-center  text-sm">{el.title}</p>
                    </div>
                  </a>
                ))}
              </div>
              <div className="w-[280vh] flex flex-nowrap animate-slider2 relative right-300">
                {state.map((el) => (
                  <a
                    href={`${el.youtubeLink}`}
                    className="flex flex-col items-center justify-center mx-8 my-2 text-white hover:translate-y-[15px] transition duration-300 ease-in-out"
                  >
                    <div className="w-[15vh] flex flex-col items-center">
                      <img src={el.thumbnailLink} className="w-[15vh] h-[10vh] my-4" />
                      <p className="h-[50px] text-center ">{el.artist}</p>
                      <p className="h-[50px] text-center text-sm">{el.title}</p>
                    </div>
                  </a>
                ))}
              </div>
            </div>
          )}
        </div>
        <h1 className="mt-8 font-['Anton-Regular'] text-2xl text-[#d9d9d9] ">
          담당자가 추천 해준 노래는 아래 플레이어에 담겨있습니다 !! 한 번 들어보세요 !!
        </h1>
      </div>
    </div>
  );
};

export default SurveyAdmin;
