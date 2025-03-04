import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { useSelector, useDispatch } from "react-redux";
import { getThreadByIdAction, addCommentAction, fetchCommentsByPostIdAction } from "../redux/PostService/Action";
import Post from "../components/Forum/Post";
import Comment from "../components/Forum/Comment";
import SortComments from "../components/Forum/SortComments";

function ThreadPage() {
    const { threadId } = useParams();
    const dispatch = useDispatch();
    const { currentThread, comments } = useSelector((state) => ({
        currentThread: state.thread.currentThread,
        comments: state.thread.comments,
    }));

    const [newComment, setNewComment] = useState("");
    const [isSortOpen, setIsSortOpen] = useState(false);
    const [sortOption, setSortOption] = useState(0); // Domyślnie "Data (najnowsze)"

    useEffect(() => {
        dispatch(getThreadByIdAction(threadId));
        dispatch(fetchCommentsByPostIdAction(threadId));
    }, [dispatch, threadId]);

    // useEffect(() => {
    //     if (comments) {
    //         console.log("Komentarze:", comments);
    //     }
    // }, [comments]);

    const handleCommentSubmit = async (e) => {
        e.preventDefault();

        if (!newComment.trim()) {
            alert("Treść komentarza nie może być pusta.");
            return;
        }

        const commentPayload = {
            content: newComment,
            postId: parseInt(threadId),
            parentCommentId: null,
        };

        try {
            await dispatch(addCommentAction(commentPayload));
            setNewComment("");
            dispatch(fetchCommentsByPostIdAction(threadId));
        } catch (error) {
            console.error("Wystąpił błąd podczas dodawania komentarza:", error);
            alert("Nie udało się dodać komentarza. Spróbuj ponownie.");
        }
    };

    const handleReply = async (parentCommentId, replyContent) => {
        const replyPayload = {
            content: replyContent,
            postId: parseInt(threadId),
            parentCommentId: parentCommentId,
        };

        try {
            await dispatch(addCommentAction(replyPayload));
            dispatch(fetchCommentsByPostIdAction(threadId));
        } catch (error) {
            console.error("Błąd podczas dodawania odpowiedzi:", error);
            alert("Nie udało się dodać odpowiedzi. Spróbuj ponownie.");
        }
    };

    const sortComments = (comments) => {
        switch (sortOption) {
            case 0: // Data (najnowsze)
                return [...comments].sort((a, b) => new Date(b.createDate) - new Date(a.createDate));
            case 1: // Data (najstarsze)
                return [...comments].sort((a, b) => new Date(a.createDate) - new Date(b.createDate));
            case 2: // Oceny
                return [...comments].sort((a, b) => b.score - a.score);
            default:
                return comments;
        }
    };

    const sortedComments = sortComments(comments);

    if (!currentThread) {
        return (
            <div className="p-32 flex justify-center items-center">
                <p className="text-lg text-gray-500">Wątek nie został znaleziony.</p>
            </div>
        );
    }

    return (
        <div className="p-32">
            <div className="max-w-4xl mx-auto">
                <Post
                    postId={currentThread?.id}
                    title={currentThread?.title || "Tytuł niedostępny"}
                    categories={currentThread?.categories || "Kategoria niedostępna"}
                    date={
                        currentThread?.createDate
                            ? new Date(currentThread?.createDate).toLocaleString()
                            : "Nieznana data"
                    }
                    message={currentThread?.content || "Treść niedostępna"}
                    score={currentThread?.score || 0}
                    replies={currentThread?.replies || 0}
                    author={currentThread?.author || "Nieznany"}
                    commentsCount={currentThread?.commentsCount || ""}
                />
            </div>

            <div className="mt-10 max-w-4xl mx-auto">
                <h2 className="text-lg font-semibold mb-4">Dodaj komentarz:</h2>
                <form onSubmit={handleCommentSubmit} className="flex items-center gap-4">
                    <div className="relative w-full">
                        <input
                            type="text"
                            placeholder="Wpisz treść komentarza..."
                            className="w-full px-4 py-2 border border-gray-300 rounded-full focus:outline-none focus:ring-2 focus:ring-blue-500"
                            value={newComment}
                            onChange={(e) => setNewComment(e.target.value)}
                        />
                    </div>
                    <button
                        type="submit"
                        className="bg-blue-500 text-white font-semibold py-2 px-6 rounded-full hover:bg-blue-600 transition-colors whitespace-nowrap"
                    >
                        Dodaj komentarz
                    </button>
                </form>
            </div>

            <div className="relative mt-10 max-w-4xl mx-auto">
                <button
                    className="px-6 py-2 bg-blue-900 text-white rounded-full hover:bg-blue-800"
                    onClick={() => setIsSortOpen(!isSortOpen)}
                >
                    Sortuj po: {["Data (najnowsze)", "Data (najstarsze)", "Oceny"][sortOption]}
                </button>

                {isSortOpen && (
                    <div className="absolute mt-2 bg-white shadow-lg rounded-md z-10">
                        <SortComments
                            onSortChange={(option) => {
                                setSortOption(option);
                                setIsSortOpen(false);
                            }}
                        />
                    </div>
                )}
            </div>

            <div className="mt-10 max-w-4xl mx-auto">
                <h2 className="text-lg font-semibold mb-4">Komentarze:</h2>
                {sortedComments && sortedComments.length > 0 ? (
                <div className="space-y-4">
                    {sortedComments.map((comment) => (
                        <Comment
                            key={comment.id}
                            commentId={comment.id}
                            content={comment.content}
                            author={comment.author}
                            createDate={comment.createDate ? new Date(comment.createDate).toLocaleString() : "Nieznana data"}
                            score={comment.score || 0}
                            replies={comment.replies || []}
                            onReply={handleReply}
                        />
                    ))}
                </div>
                ) : (
                <p className="text-gray-500">Brak komentarzy do wyświetlenia.</p>
                )}
            </div>
        </div>
    );
}

export { ThreadPage };
