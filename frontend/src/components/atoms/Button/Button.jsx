import { Button as AntButton } from 'antd';
import PropTypes from 'prop-types';
import './Button.css';

const Button = ({
  children,
  variant = 'primary',
  size = 'medium',
  icon,
  loading,
  disabled,
  block,
  danger,
  ghost,
  onClick,
  htmlType = 'button',
  className = '',
  ...props
}) => {
  const getType = () => {
    if (variant === 'primary') return 'primary';
    if (variant === 'secondary') return 'default';
    if (variant === 'text') return 'text';
    if (variant === 'link') return 'link';
    return 'default';
  };

  const getSize = () => {
    if (size === 'small') return 'small';
    if (size === 'large') return 'large';
    return 'middle';
  };

  return (
    <AntButton
      type={getType()}
      size={getSize()}
      icon={icon}
      loading={loading}
      disabled={disabled}
      block={block}
      danger={danger}
      ghost={ghost}
      onClick={onClick}
      htmlType={htmlType}
      className={`custom-button custom-button--${variant} custom-button--${size} ${className}`}
      {...props}
    >
      {children}
    </AntButton>
  );
};

Button.propTypes = {
  children: PropTypes.node,
  variant: PropTypes.oneOf(['primary', 'secondary', 'success', 'danger', 'text', 'link']),
  size: PropTypes.oneOf(['small', 'medium', 'large']),
  icon: PropTypes.node,
  loading: PropTypes.bool,
  disabled: PropTypes.bool,
  block: PropTypes.bool,
  danger: PropTypes.bool,
  ghost: PropTypes.bool,
  onClick: PropTypes.func,
  htmlType: PropTypes.oneOf(['button', 'submit', 'reset']),
  className: PropTypes.string,
};

export default Button;
