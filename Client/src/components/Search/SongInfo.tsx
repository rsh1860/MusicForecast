import { useSelector } from 'react-redux';
import Logo from '../../assets/images/logo.png';
import { RootState } from '../../redux/store';
import AlbumFirst from '../../assets/images/AlbumFirst.png';

const SongInfo = () => {
  const songInfo = useSelector((state: RootState) => state.songlists.songInfo);

  return (
    <>
      {/* 앨범표지 */}
      <div className="w-full h-[12vh] flex justify-start items-center my-2">
        <img src={songInfo.imageUrl || AlbumFirst} className="w-[9vh] h-[9vh] ml-12" />
        <div className="flex flex-col justify-around ml-4">
          <p className="text-xs font-['Anton-Regular']">Album : {songInfo.albumName}</p>
          <h1 className="text-xl font-['Anton-Regular'] my-2">Title : {songInfo.title}</h1>
          <p className="text-xs font-['Anton-Regular']">Artist : {songInfo.artistName}</p>
          <div className="w-[9vh] flex items-center mt-2">
            <img src={Logo} className="w-[100px] h-[20px]" />
          </div>
        </div>
      </div>
    </>
  );
};

export default SongInfo;
