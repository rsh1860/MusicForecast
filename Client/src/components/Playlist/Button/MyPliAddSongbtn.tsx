import { useDispatch, useSelector } from 'react-redux';
import { toast } from 'react-toastify';
import { RootState } from '../../../redux/store';
import { openRecommendModal } from '../../../redux/slice/ModalSlice';
import RecommendAdd from '../../Modal/RecommendAdd';

const MyPliAddSongbtn = () => {
  const dispatch = useDispatch();
  const isRecommendOpen = useSelector((state: RootState) => state.modal.isRecommendOpen);

  const handleAddSong = () => {
    dispatch(openRecommendModal());
    toast.info('🎤노래 추가를 원하시면 넣으실 해당 플레이리스트를 클릭해주세요!', {
      position: 'top-right',
    });
  };

  return (
    <>
      {isRecommendOpen ? (
        <RecommendAdd />
      ) : (
        <button
          onClick={handleAddSong}
          className="w-[15vh] h-[5vh] mb-4 mr-4 rounded-2xl border-2 border-purple-400 hover:bg-[#9574b1] hover:text-white text-sm"
        >
          내 플리에 추가하기
        </button>
      )}
    </>
  );
};

export default MyPliAddSongbtn;
