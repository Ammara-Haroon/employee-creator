import { faXmark } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";

const ErrMsg = ({ msg, onClose }: { msg: string; onClose: () => unknown }) => {
  return (
    <div className="fixed h-fit bg-sky-100 rounded-lg w-fit px-10 py-10  right-12 bottom-10 border">
      <FontAwesomeIcon
        className="hover:text-cyan-900 p-2 absolute top-0 right-0 text-teal-500 text-right"
        icon={faXmark}
        onClick={onClose}
      ></FontAwesomeIcon>
      <p className="text-center text-slate-600">{msg}</p>
    </div>
  );
};

export default ErrMsg;
