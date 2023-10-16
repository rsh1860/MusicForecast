import SongLists from '../components/Search/Songlists';
import Header from '../components/Header';
import SongBtn from '../components/Search/Button/SongBtn';
import SongInfo from '../components/Search/SongInfo';
import SearchInput from '../components/Search/SearchInput';
import PlaylistIcon from '../components/Playlist/PlaylistIcon';
import Wrapper from '../components/Wrapper/Wrapper';

const SearchSongs = () => {
  return (
    <>
      <div className="w-screen h-screen bg-gradient-to-b from-[#D5E5F0] to-[#87c4ed]">
        <Header />
        <Wrapper>
          <SearchInput />
          <div className="w-full flex justify-center">
            <div className="w-[100vh] h-[60vh] mt-8 bg-gradient-to-b from-[#97c7f3] to-[#fafafa7c] shadow-xl rounded-2xl">
              <SongInfo />
              <SongLists />
              <SongBtn />
            </div>
          </div>
        </Wrapper>
      </div>
      <PlaylistIcon />
    </>
  );
};

export default SearchSongs;
