import { useDispatch } from 'react-redux';
import { openToastModal } from '../../../redux/slice/ModalSlice';

const PlaylistAddButton = () => {
  const dispatch = useDispatch();

  const handleOpenToast = () => {
    dispatch(openToastModal());
  };
  return (
    <div className="flex justify-center mt-8">
      <button
        onClick={handleOpenToast}
        className="w-[150px] h-[30px] mb-4 mr-4 rounded-2xl border-2 text-sm border-purple-400 hover:bg-[#9574b1] hover:text-white"
      >
        플레이리스트 추가
      </button>
    </div>
  );
};

export default PlaylistAddButton;
