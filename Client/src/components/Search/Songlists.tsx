import { useState } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { RootState } from '../../redux/store';
import { setSelectedSongs, selectedSonglist } from '../../redux/slice/SonglistsSlice';

const SongLists = () => {
  const [selectedItems, setSelectedItems] = useState([]); // 선택된 항목을 저장할 상태
  const SongData = useSelector((state: RootState) => state.songlists.value);

  const dispatch = useDispatch();

  const handleSongClick = (el: object) => {
    const isSelected = selectedItems.includes(el);

    let updatedSelectedItems;

    if (isSelected) {
      updatedSelectedItems = selectedItems.filter((item) => item !== el);
    } else {
      updatedSelectedItems = [...selectedItems, el];
    }

    // setSelectedItems를 사용하여 selectedItems 배열을 업데이트합니다.
    setSelectedItems(updatedSelectedItems);

    // Redux 스토어에 업데이트된 selectedItems 배열을 전달합니다.
    dispatch(setSelectedSongs(updatedSelectedItems));
    dispatch(selectedSonglist(el));
  };

  return (
    <>
      {/* 플리 앨범, 제목, 내용 */}
      <div className="w-full h-[50px] grid grid-cols-10 text-center items-center font-['Anton-Regular'] border-y-2 border-solid border-blue-400">
        <h3 className="col-span-1">#</h3>
        <h3 className="col-span-1">No.</h3>
        <h3 className="col-span-2">Artist</h3>
        <h3 className="col-span-2">Album Image</h3>
        <h3 className="col-span-2">Album Title</h3>
        <h3 className="col-span-2">Title</h3>
      </div>

      {/* 플리 노래목록 */}
      <ul className="w-full h-[30vh] flex flex-col overflow-y-scroll">
        {SongData.map((el, index) => (
          <li
            className={`w-full h-[60px] grid grid-cols-10 items-center text-center border-b-2 border-solid border-gray-400 border-opacity-20 ${
              selectedItems.includes(el) ? 'bg-blue-200' : ''
            }`}
            key={index}
            onClick={() => handleSongClick(el)} // li 요소 클릭 시 토글 함수 호출
          >
            <input
              type="checkbox"
              className="col-span-1"
              onClick={() => handleSongClick(el)}
              checked={selectedItems.includes(el)}
            />
            <h3 className="col-span-1">{index + 1}</h3>

            <p className="text-sm col-span-2 font-bold">{el.artistName}</p>
            <div className="h-[30px] flex flex-col items-center my-4 col-span-2">
              <img src={el.imageUrl} className="w-[50px] h-[30px] " />
            </div>
            <h3 className="col-span-2 text-sm">{el.albumName}</h3>
            <h3 className="col-span-2 text-sm font-bold">{el.title}</h3>
          </li>
        ))}
      </ul>
    </>
  );
};

export default SongLists;
