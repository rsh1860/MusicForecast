import { KeyboardEvent } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { openModal, closeModal } from '../../redux/slice/ModalSlice';
// import playIcon2 from '../../assets/images/playicon2.png';
import playIcon from '../../assets/images/playicon.png';
import PlaylistModal from '../Modal/PlaylistModal';
import { RootState } from '../../redux/store';

const playlistIcon = () => {
  const dispatch = useDispatch();
  const isOpen = useSelector((state: RootState) => state.modal.isOpen);

  // 모달을 열고 닫는 함수
  const toggleModal = () => {
    if (isOpen) {
      dispatch(closeModal()); // 모달이 열려 있을 때 닫음
    } else {
      dispatch(openModal()); // 모달이 닫혀 있을 때 열음
    }
  };

  // Enter 키 입력 이벤트 핸들러 함수
  function handleEnterKeyPress(e: KeyboardEvent<HTMLButtonElement>) {
    if (e.key === 'Enter') {
      toggleModal(); // Enter 키 눌렀을 때 모달 열고 닫기
    }
  }

  return (
    <>
      <button
        onClick={toggleModal}
        onKeyPress={handleEnterKeyPress}
        tabIndex={0}
        className="w-[10vh] h-[10vh] fixed right-0 bottom-0 mr-8 mb-8 z-30 bg-white rounded-[30%] border-2 border-[#444]"
      >
        <img
          src={playIcon}
          alt="playIcon"
          className="fixed right-0 bottom-0 mr-8 mb-8 hover:animate-bounceIn w-[10vh] h-[10vh]"
        />
      </button>
      {isOpen && <PlaylistModal />}
    </>
  );
};

export default playlistIcon;
