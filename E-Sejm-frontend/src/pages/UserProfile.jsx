import React, {useEffect, useState} from "react";
import {FaCog, FaEdit} from "react-icons/fa";
import {useNavigate, useParams} from "react-router-dom";
import {useDispatch, useSelector} from "react-redux";
import {getUserByIdAction} from "../redux/UserService/Action.js";
import {ProfileEditor} from "../components/ProfileEditor.jsx";

const UserProfile = () => {
    const navigate = useNavigate();
    const dispatch = useDispatch();
    const {nickName} = useParams();
    const [editPicture, setEditPicture] = useState(false)
    const { getUser,editUser} = useSelector(store => store.user);


    useEffect(() => {
        dispatch(getUserByIdAction(nickName));
    }, [dispatch, nickName, editUser]);

    return (
        <div className="min-h-screen">
            <div className="container mx-auto mt-16">
                {/*<div className="w-full h-56 mx-auto rounded-lg overflow-hidden shadow-md">*/}
                {/*    <img*/}
                {/*        src="https://static.vecteezy.com/system/resources/thumbnails/009/292/244/small/default-avatar-icon-of-social-media-user-vector.jpg"*/}
                {/*        alt="Konfederacja Baner"*/}
                {/*        className="w-full h-full object-cover"*/}
                {/*    />*/}
                {/*</div>*/}
            </div>

            <div className="container mx-auto flex flex-col lg:flex-row mt-8 px-4 gap-6">
                <aside className="bg-[#1d3461] text-white rounded-lg p-6 w-full lg:w-1/4 shadow-lg">
                    <div className="text-center relative">
                        <div className="relative inline-block">
                            <img
                                src={getUser?.user.profileImage || 'https://static.vecteezy.com/system/resources/thumbnails/009/292/244/small/default-avatar-icon-of-social-media-user-vector.jpg'}
                                alt="User Avatar"
                                className="w-32 h-32 rounded-full object-cover"
                            />
                            <button
                                className="absolute bottom-2 right-0 transition-colors duration-300"
                                onClick={() => setEditPicture(((prev) => !prev))}>
                                <FaEdit className="text-white-700  hover:text-gray-300" size={24}/>
                            </button>
                        </div>
                        <h2 className="text-xl font-black font-['Inter'] mb-4">{getUser?.user.email}</h2>
                    </div>
                    <div className="space-y-2 text-center">
                        <p className="text-lg font-semibold">Imię: {getUser?.user.firstName}</p>
                        <p className="text-lg font-semibold">Nazwisko: {getUser?.user.lastName}</p>
                        <p className="text-lg font-semibold">Wiek: 21</p>
                        <p className="text-lg font-semibold">Lokalizacja: Augustów</p>
                        <p className="text-lg font-semibold">Płeć: Mężczyzna</p>
                    </div>
                    <div className="mt-4 space-y-2 text-center">
                        <p className="text-lg font-bold font-['Rosario']">Liczba głosów: 123</p>
                        <p className="text-lg font-bold font-['Rosario']">Dyskusje: 45</p>
                        <p className="text-lg font-bold font-['Rosario']">Obserwowani politycy: 5</p>
                    </div>
                    <button
                        onClick={() => navigate("/user-settings")}
                        className="bg-[#274561] hover:bg-[#1f487e] text-white py-2 px-4 rounded mt-6 mx-auto flex items-center justify-center gap-2"
                    >
                        <FaCog className="text-white w-5 h-5"/>
                        Ustawienia
                    </button>
                </aside>

                <main className="flex-1">
                    <section className="mb-8 text-center">
                        <h2 className="text-3xl font-black text-[#1d3461] mb-4 font-['Inter']">
                            Ostatnia Aktywność użytkownika:
                        </h2>
                        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4">
                            {Array(4).fill(null).map((_, index) => (
                                <div
                                    key={index}
                                    className="bg-[#608FC7] rounded-lg shadow-md p-4"
                                >
                                    <h3 className="text-center text-lg font-bold np font-['Inter']">KOMENTARZ</h3>
                                    <p className="text-center text-sm text-black mt-2 font-['Inter']">
                                        Wybór Najbardziej Zaufanego Polityka
                                    </p>
                                    <button
                                        className="bg-[#1f487e] text-white hover:bg-[#274561] py-2 px-4 rounded mt-4 w-full">
                                        Zobacz
                                    </button>
                                </div>
                            ))}
                        </div>
                        <button className="bg-[#1f487e] text-white hover:bg-[#274561] py-2 px-6 rounded mt-6 mx-auto block">
                            Cała aktywność
                        </button>
                    </section>

                    <section className="text-center">
                        <h2 className="text-3xl font-black text-[#1d3461] mb-4 font-['Inter']">
                            Ostatnio obserwowani politycy:
                        </h2>
                        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4">
                            {Array(4).fill(null).map((_, index) => (
                                <div
                                    key={index}
                                    className="bg-[#608FC7] rounded-lg shadow-md p-4 text-center"
                                >
                                    <img
                                        src="https://i.gremicdn.pl/image/free/b9b2ab888f8d0949fa09bb69e5a230c5/?t=crop:1742:1080:nowe:111:0,resize:fill:948:593,enlarge:1"
                                        alt="Grzegorz Braun"
                                        className="w-full aspect-w-16 aspect-h-9 max-w-[150px] mx-auto rounded-lg"
                                    />
                                    <h3 className="text-lg font-bold font-['Jaini Purva'] mt-4">Grzegorz Braun</h3>
                                    <p className="text-xl font-normal text-black font-['Kalam']">Konfederacja</p>
                                    <button
                                        className="bg-[#1f487e] text-white hover:bg-[#274561] py-2 px-4 rounded mt-4 w-full">
                                        Zobacz
                                    </button>
                                </div>
                            ))}
                        </div>
                        <button className="bg-[#1f487e] text-white hover:bg-[#274561] py-2 px-6 rounded mt-6 mx-auto block">
                            Wszyscy obserwowani
                        </button>
                    </section>
                </main>
            </div>
            {editPicture && <ProfileEditor onClose={() => setEditPicture(((prev) => !prev))} profileImg={getUser?.user.profileImage}/>}
        </div>
    );
};

export default UserProfile;
