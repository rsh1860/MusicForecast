import { useSelector, useDispatch } from 'react-redux';
import { RootState } from '../../../redux/store';
import playlistdisc from '../../../assets/images/playlistdisc.png';
import Header from '../../../components/Header';
import { openModal, openSongLists } from '../../../redux/slice/ModalSlice';
import { setSelectedPlaylistId, setPlaylistTitle } from '../../../redux/slice/PlaylistsSlice';
import PlaylistIcon from '../../../components/Playlist/PlaylistIcon';

const SurveyResult = () => {
  const dispatch = useDispatch();
  const surveyResult = useSelector((state: RootState) => state.mubti.Result);
  console.log(surveyResult);

  const handleDetailOpen = async (playlistId: number, title: string) => {
    dispatch(openModal());

    setTimeout(async () => {
      await dispatch(openSongLists());
      dispatch(setSelectedPlaylistId(playlistId));
      dispatch(setPlaylistTitle(title));
    }, 1500);
  };

  return (
    <div className="h-screen bg-[#35435e]">
      <Header />
      <div className="flex flex-col justify-center items-center mt-40">
        <div className="flex flex-row justify-between items-center w-[250px] ">
          <button className="w-[20px] h-[20px] bg-[#d9d9d9] rounded-full"></button>
          <button className="w-[20px] h-[20px] bg-[#d9d9d9] rounded-full "></button>
          <button className="w-[20px] h-[20px] bg-[#d9d9d9] rounded-full"></button>
          <button className="w-[20px] h-[20px] bg-[#797676] rounded-full border-2 border-red-100"></button>
        </div>
        <h1 className="flex my-8 font-['Anton-Regular'] text-[#d9d9d9] text-2xl font-semibold">
          두구두구둑두구,,,, 이런 장르는 어떨까요 !?
        </h1>
        <div>
          <h1 className="w-full flex justify-start my-8 ml-6 font-['Anton-Regular'] text-2xl text-[#d9d9d9] ">
            Recommend
          </h1>
          <div className="flex ">
            {surveyResult.map((el) => (
              <li
                onClick={() => handleDetailOpen(el.playlistId, el.title)}
                className="flex flex-col items-center justify-center mx-8 text-white cursor-pointer hover:translate-y-[-15px] transition duration-300 ease-in-out"
              >
                <div className="w-[150px] ">
                  <img src={playlistdisc} className="animate-spin-slow w-[150px] h-[150px] my-4" />
                  <p className="h-[50px] text-center ">{el.title}</p>
                </div>
              </li>
            ))}
          </div>
        </div>
      </div>
      <PlaylistIcon />
    </div>
  );
};

export default SurveyResult;
